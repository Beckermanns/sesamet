import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAtencion, NewAtencion } from '../atencion.model';

export type PartialUpdateAtencion = Partial<IAtencion> & Pick<IAtencion, 'id'>;

type RestOf<T extends IAtencion | NewAtencion> = Omit<T, 'fecha'> & {
  fecha?: string | null;
};

export type RestAtencion = RestOf<IAtencion>;

export type NewRestAtencion = RestOf<NewAtencion>;

export type PartialUpdateRestAtencion = RestOf<PartialUpdateAtencion>;

export type EntityResponseType = HttpResponse<IAtencion>;
export type EntityArrayResponseType = HttpResponse<IAtencion[]>;

@Injectable({ providedIn: 'root' })
export class AtencionService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/atencions');

  create(atencion: NewAtencion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(atencion);
    return this.http
      .post<RestAtencion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(atencion: IAtencion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(atencion);
    return this.http
      .put<RestAtencion>(`${this.resourceUrl}/${this.getAtencionIdentifier(atencion)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(atencion: PartialUpdateAtencion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(atencion);
    return this.http
      .patch<RestAtencion>(`${this.resourceUrl}/${this.getAtencionIdentifier(atencion)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAtencion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAtencion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAtencionIdentifier(atencion: Pick<IAtencion, 'id'>): number {
    return atencion.id;
  }

  compareAtencion(o1: Pick<IAtencion, 'id'> | null, o2: Pick<IAtencion, 'id'> | null): boolean {
    return o1 && o2 ? this.getAtencionIdentifier(o1) === this.getAtencionIdentifier(o2) : o1 === o2;
  }

  addAtencionToCollectionIfMissing<Type extends Pick<IAtencion, 'id'>>(
    atencionCollection: Type[],
    ...atencionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const atencions: Type[] = atencionsToCheck.filter(isPresent);
    if (atencions.length > 0) {
      const atencionCollectionIdentifiers = atencionCollection.map(atencionItem => this.getAtencionIdentifier(atencionItem));
      const atencionsToAdd = atencions.filter(atencionItem => {
        const atencionIdentifier = this.getAtencionIdentifier(atencionItem);
        if (atencionCollectionIdentifiers.includes(atencionIdentifier)) {
          return false;
        }
        atencionCollectionIdentifiers.push(atencionIdentifier);
        return true;
      });
      return [...atencionsToAdd, ...atencionCollection];
    }
    return atencionCollection;
  }

  protected convertDateFromClient<T extends IAtencion | NewAtencion | PartialUpdateAtencion>(atencion: T): RestOf<T> {
    return {
      ...atencion,
      fecha: atencion.fecha?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restAtencion: RestAtencion): IAtencion {
    return {
      ...restAtencion,
      fecha: restAtencion.fecha ? dayjs(restAtencion.fecha) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAtencion>): HttpResponse<IAtencion> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAtencion[]>): HttpResponse<IAtencion[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
