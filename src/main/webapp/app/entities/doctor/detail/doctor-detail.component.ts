import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IDoctor } from '../doctor.model';

@Component({
  standalone: true,
  selector: 'jhi-doctor-detail',
  templateUrl: './doctor-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DoctorDetailComponent {
  doctor = input<IDoctor | null>(null);

  previousState(): void {
    window.history.back();
  }
}
