import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {
  private errorSource: BehaviorSubject<string> = new BehaviorSubject('');

  public errorMessage: Observable<string> = this.errorSource.asObservable();

  constructor() {}

  changeErrorMessage(error: string) {
    this.errorSource.next(error);
  }
}
