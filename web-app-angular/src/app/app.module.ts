import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { UserViewNewsModule } from './component/user-view-news/user-view-news.module';
import { NavModule } from './component/nav/nav.module';
import { SecurityModule } from './component/security/security.module';
@NgModule({
  declarations: [
    AppComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    UserViewNewsModule,
    // NavModule,
    // SecurityModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
