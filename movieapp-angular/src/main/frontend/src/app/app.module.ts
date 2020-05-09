import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import { AppComponent } from './app.component';
import {FormsModule} from "@angular/forms";
import { MoviesComponent } from './admin/movies/movies.component';
import {RouterModule, Routes} from "@angular/router";
import { Page404Component } from './page404/page404.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { FavoriteComponent } from './favorite/favorite.component';
import {authInterceptorProviders} from "./service/auth.interceptor.service";

const routes: Routes = [
  {'path' : 'admin/movies', component: MoviesComponent},
  {'path' : 'login', component: LoginComponent},
  {'path' : 'register', component: RegisterComponent},
  {'path' : 'favorite', component: FavoriteComponent},
  {'path' : '404', component: Page404Component},
  {'path' : '', component: HomeComponent},
  {'path' : '**', redirectTo: '404'}
]

@NgModule({
  declarations: [
    AppComponent,
    MoviesComponent,
    Page404Component,
    NavBarComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    FavoriteComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes)
  ],
  providers: [
    authInterceptorProviders
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
