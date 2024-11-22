import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAtencion } from '../atencion.model';
import { AtencionService } from '../service/atencion.service';

@Component({
  standalone: true,
  templateUrl: './atencion-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AtencionDeleteDialogComponent {
  atencion?: IAtencion;

  protected atencionService = inject(AtencionService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.atencionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
