import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICentroMedico } from 'app/entities/centro-medico/centro-medico.model';
import { CentroMedicoService } from 'app/entities/centro-medico/service/centro-medico.service';
import { IDoctor } from 'app/entities/doctor/doctor.model';
import { DoctorService } from 'app/entities/doctor/service/doctor.service';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';
import { AtencionService } from '../service/atencion.service';
import { IAtencion } from '../atencion.model';
import { AtencionFormGroup, AtencionFormService } from './atencion-form.service';

@Component({
  standalone: true,
  selector: 'jhi-atencion-update',
  templateUrl: './atencion-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AtencionUpdateComponent implements OnInit {
  isSaving = false;
  atencion: IAtencion | null = null;

  centroMedicosSharedCollection: ICentroMedico[] = [];
  doctorsSharedCollection: IDoctor[] = [];
  pacientesSharedCollection: IPaciente[] = [];

  protected atencionService = inject(AtencionService);
  protected atencionFormService = inject(AtencionFormService);
  protected centroMedicoService = inject(CentroMedicoService);
  protected doctorService = inject(DoctorService);
  protected pacienteService = inject(PacienteService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AtencionFormGroup = this.atencionFormService.createAtencionFormGroup();

  compareCentroMedico = (o1: ICentroMedico | null, o2: ICentroMedico | null): boolean =>
    this.centroMedicoService.compareCentroMedico(o1, o2);

  compareDoctor = (o1: IDoctor | null, o2: IDoctor | null): boolean => this.doctorService.compareDoctor(o1, o2);

  comparePaciente = (o1: IPaciente | null, o2: IPaciente | null): boolean => this.pacienteService.comparePaciente(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ atencion }) => {
      this.atencion = atencion;
      if (atencion) {
        this.updateForm(atencion);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const atencion = this.atencionFormService.getAtencion(this.editForm);
    if (atencion.id !== null) {
      this.subscribeToSaveResponse(this.atencionService.update(atencion));
    } else {
      this.subscribeToSaveResponse(this.atencionService.create(atencion));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAtencion>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(atencion: IAtencion): void {
    this.atencion = atencion;
    this.atencionFormService.resetForm(this.editForm, atencion);

    this.centroMedicosSharedCollection = this.centroMedicoService.addCentroMedicoToCollectionIfMissing<ICentroMedico>(
      this.centroMedicosSharedCollection,
      atencion.centroMedico,
    );
    this.doctorsSharedCollection = this.doctorService.addDoctorToCollectionIfMissing<IDoctor>(
      this.doctorsSharedCollection,
      atencion.doctor,
    );
    this.pacientesSharedCollection = this.pacienteService.addPacienteToCollectionIfMissing<IPaciente>(
      this.pacientesSharedCollection,
      atencion.paciente,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.centroMedicoService
      .query()
      .pipe(map((res: HttpResponse<ICentroMedico[]>) => res.body ?? []))
      .pipe(
        map((centroMedicos: ICentroMedico[]) =>
          this.centroMedicoService.addCentroMedicoToCollectionIfMissing<ICentroMedico>(centroMedicos, this.atencion?.centroMedico),
        ),
      )
      .subscribe((centroMedicos: ICentroMedico[]) => (this.centroMedicosSharedCollection = centroMedicos));

    this.doctorService
      .query()
      .pipe(map((res: HttpResponse<IDoctor[]>) => res.body ?? []))
      .pipe(map((doctors: IDoctor[]) => this.doctorService.addDoctorToCollectionIfMissing<IDoctor>(doctors, this.atencion?.doctor)))
      .subscribe((doctors: IDoctor[]) => (this.doctorsSharedCollection = doctors));

    this.pacienteService
      .query()
      .pipe(map((res: HttpResponse<IPaciente[]>) => res.body ?? []))
      .pipe(
        map((pacientes: IPaciente[]) =>
          this.pacienteService.addPacienteToCollectionIfMissing<IPaciente>(pacientes, this.atencion?.paciente),
        ),
      )
      .subscribe((pacientes: IPaciente[]) => (this.pacientesSharedCollection = pacientes));
  }
}
