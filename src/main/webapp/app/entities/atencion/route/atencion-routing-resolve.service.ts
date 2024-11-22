import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAtencion } from '../atencion.model';
import { AtencionService } from '../service/atencion.service';

const atencionResolve = (route: ActivatedRouteSnapshot): Observable<null | IAtencion> => {
  const id = route.params.id;
  if (id) {
    return inject(AtencionService)
      .find(id)
      .pipe(
        mergeMap((atencion: HttpResponse<IAtencion>) => {
          if (atencion.body) {
            return of(atencion.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default atencionResolve;
