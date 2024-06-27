import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserViewHomeComponent } from './user-view-home.component';

describe('UserViewHomeComponent', () => {
  let component: UserViewHomeComponent;
  let fixture: ComponentFixture<UserViewHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserViewHomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserViewHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
