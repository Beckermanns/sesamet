import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ICentroMedico } from '../centro-medico.model';

@Component({
  standalone: true,
  selector: 'jhi-centro-medico-detail',
  templateUrl: './centro-medico-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CentroMedicoDetailComponent {
  centroMedico = input<ICentroMedico | null>(null);

  previousState(): void {
    window.history.back();
  }
}
