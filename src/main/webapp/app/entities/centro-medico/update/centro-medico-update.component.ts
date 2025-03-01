import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICentroMedico } from '../centro-medico.model';
import { CentroMedicoService } from '../service/centro-medico.service';
import { CentroMedicoFormGroup, CentroMedicoFormService } from './centro-medico-form.service';

@Component({
  standalone: true,
  selector: 'jhi-centro-medico-update',
  templateUrl: './centro-medico-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CentroMedicoUpdateComponent implements OnInit {
  isSaving = false;
  centroMedico: ICentroMedico | null = null;

  protected centroMedicoService = inject(CentroMedicoService);
  protected centroMedicoFormService = inject(CentroMedicoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CentroMedicoFormGroup = this.centroMedicoFormService.createCentroMedicoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ centroMedico }) => {
      this.centroMedico = centroMedico;
      if (centroMedico) {
        this.updateForm(centroMedico);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const centroMedico = this.centroMedicoFormService.getCentroMedico(this.editForm);
    if (centroMedico.id !== null) {
      this.subscribeToSaveResponse(this.centroMedicoService.update(centroMedico));
    } else {
      this.subscribeToSaveResponse(this.centroMedicoService.create(centroMedico));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICentroMedico>>): void {
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

  protected updateForm(centroMedico: ICentroMedico): void {
    this.centroMedico = centroMedico;
    this.centroMedicoFormService.resetForm(this.editForm, centroMedico);
  }
}
