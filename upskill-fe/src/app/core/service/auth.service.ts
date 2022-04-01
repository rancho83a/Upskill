import { HttpResponse, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ILogin, ICheckCompanyName, ICurrentUser } from '../model';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from '../../../environments/environment';
import { authSelector } from '../../+store';
import { Store } from '@ngrx/store';
import { map, take } from 'rxjs/operators';

const LOGIN= '/auth/auth/token';
const CHECK_COMPANY = '/profile/company/name';
const GET_CURRENT_USER ='/profile/profile/user'

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private http: HttpClient,
    private jwtService: JwtHelperService,
    private store: Store
  ) {}

  login(user: ILogin): Observable<HttpResponse<ILogin>> {
    return this.http.post<any>(
      environment.apiUrl + LOGIN,
      user,
      {
        observe: 'response',
      }
    );
  }

  getTokenData(token: string) {
    if (!token) {
      return;
    }
    const decodeToken = this.jwtService.decodeToken(token);
    const role = decodeToken.role;
    const email = decodeToken.email;
    localStorage.setItem('token', token.substring(token.indexOf(' ')));
    return {
      email,
      role,
      token: token.substring(token.indexOf(' ')),
    };
  }

  findCompanyByName(companyName: ICheckCompanyName): Observable<any> {
    return this.http.post<any>(
       environment.apiUrl + CHECK_COMPANY,
      companyName
    );
  }




  isAuthenticated$():Observable<any> {
    return this.store.select(authSelector.getToken);
  }

  isEmployee$(): Observable<boolean> {
    return this.store.select(authSelector.getRole).pipe(
      map((r) => {
        if (r.includes('EMPLOYEE_ROLE')) {
          return true;
        }
        return false;
      })
    );    
  }

  isBusinessOwner$(): Observable<boolean> {
    return this.store.select(authSelector.getRole).pipe(
      map((r) => {
        if (r.includes('COMPANY_ROLE')) {
          return true;
        }
        return false;
      })
    );     
  }

  isAdmin$(): Observable<boolean> {
    return this.store.select(authSelector.getRole).pipe(
      map((r) => {
        if (r.includes('ADMIN_ROLE')) {
          return true;
        }
        return false;
      })
    );   
  }

  getCurrentUser$():Observable<ICurrentUser>{
    return this.http.get<ICurrentUser>(environment.apiUrl+ GET_CURRENT_USER);
  }

}
