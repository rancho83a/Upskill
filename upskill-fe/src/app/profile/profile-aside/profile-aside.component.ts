import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../../core/service/auth.service';
import { Store } from '@ngrx/store';
import { logout } from '../../+store/login/action';
import { Router } from '@angular/router';
import { ICurrentUser } from 'src/app/core/model';
import { take } from 'rxjs/operators';

@Component({
  selector: 'app-profile-aside',
  templateUrl: './profile-aside.component.html',
  styleUrls: ['./profile-aside.component.scss'],
})
export class ProfileAsideComponent implements OnInit {
  isBusinessOwner$: Observable<boolean> = this.service.isBusinessOwner$();
  isEmployee$: Observable<boolean> = this.service.isEmployee$();
  isAdmin$: Observable<boolean> = this.service.isAdmin$();
  getCurrentUser$:Observable<ICurrentUser> = this.service.getCurrentUser$();

  constructor(
    private store: Store,
    private router: Router,
    private service: AuthService
  ) {}

  ngOnInit(): void {
    this.getCurrentUser$.pipe(take(1)).subscribe(d=> console.log("current", d));
  }

  logout() {
    this.store.dispatch(logout());
    localStorage.removeItem('token');
    this.router.navigate(['home']);
  }
}
