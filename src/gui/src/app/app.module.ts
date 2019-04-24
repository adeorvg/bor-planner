import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app.routing';

import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {CustomMaterialModule} from "./core/material.module";
import {BrowserAnimationsModule, NoopAnimationsModule} from "@angular/platform-browser/animations";


@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    CustomMaterialModule,
    BrowserAnimationsModule,
    NoopAnimationsModule
  ],
  declarations:
    [
      AppComponent,
      HomeComponent,
      LoginComponent
    ],
  bootstrap:
    [AppComponent]
})

export class AppModule {
}
