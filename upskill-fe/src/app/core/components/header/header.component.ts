import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../service/auth.service';
import { NavigationStart, Router } from '@angular/router';
import { Observable } from 'rxjs';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  isAuthenticated$:Observable<any>;
  hideButtonsForLogin: boolean;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.isAuthenticated$ = this.authService.isAuthenticated$();
    this.router.events.forEach((event) => {
      if (event instanceof NavigationStart) {
        if (event.url == '/login') {
          this.hideButtonsForLogin = false;
        } else {
          this.hideButtonsForLogin = true;
        }
      }
    });
  }
}
