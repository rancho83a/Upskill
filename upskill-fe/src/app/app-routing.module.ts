import { NgModule } from '@angular/core';
import { Routes, RouterModule,PreloadAllModules } from '@angular/router';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'home' },
  { path:'auth', loadChildren:()=>import('./auth/auth.module').then(m=>m.AuthModule)},
  {path:'business-owner',loadChildren:()=>import('./profile/profile.module').then(m=>m.ProfileModule)},
]

@NgModule({
  imports: [
    RouterModule.forRoot(routes,{preloadingStrategy: PreloadAllModules}),
    
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
