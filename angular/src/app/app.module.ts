import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './login-page/login.component';
import {ReactiveFormsModule} from '@angular/forms';
import {FormInputComponent} from './form-input/form-input.component';
import {RegisterComponent} from './register-page/register.component';
import {HomePageComponent} from './home-page/home-page.component';
import {MessageComponent} from './message/message.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    FormInputComponent,
    RegisterComponent,
    HomePageComponent,
    MessageComponent
  ],
  imports: [BrowserModule, AppRoutingModule, ReactiveFormsModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
