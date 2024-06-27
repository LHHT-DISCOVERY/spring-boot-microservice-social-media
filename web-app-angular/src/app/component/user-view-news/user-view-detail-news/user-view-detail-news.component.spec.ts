import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserViewDetailNewsComponent } from './user-view-detail-news.component';

describe('UserViewDetailNewsComponent', () => {
  let component: UserViewDetailNewsComponent;
  let fixture: ComponentFixture<UserViewDetailNewsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserViewDetailNewsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserViewDetailNewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
