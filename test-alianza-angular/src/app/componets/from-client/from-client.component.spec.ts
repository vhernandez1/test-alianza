import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FromClientComponent } from './from-client.component';

describe('FromClientComponent', () => {
  let component: FromClientComponent;
  let fixture: ComponentFixture<FromClientComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FromClientComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FromClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
