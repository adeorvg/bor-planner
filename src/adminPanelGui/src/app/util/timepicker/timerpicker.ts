import {Component} from '@angular/core';
import {NgbTimepickerConfig} from '@ng-bootstrap/ng-bootstrap';
import {NgbTimeStruct} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'ngbd-timepicker-config',
  templateUrl: './timepicker.html',
  providers: [NgbTimepickerConfig]
})
export class NgbdTimepicker {
  time: NgbTimeStruct = {hour: 13, minute: 30, second: 0};

  constructor(config: NgbTimepickerConfig) {
    config.spinners = false;
  }
}