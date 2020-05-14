import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-form-input',
  templateUrl: './form-input.component.html',
  styleUrls: ['./form-input.component.scss']
})
export class FormInputComponent implements OnInit {

  @Input() type: string;
  @Input() placeholder: string;
  @Input() name: string;
  @Input() min: number;
  @Input() max: number;
  @Input() pattern: string;

  @Output() validResult: EventEmitter<boolean> = new EventEmitter<boolean>();

  input: FormControl;

  constructor() {
  }

  ngOnInit(): void {
    this.input = new FormControl('', [
          Validators.required,
          Validators.minLength(this.min),
          Validators.maxLength(this.max),
          Validators.pattern(this.pattern)
    ]);
  }

  valid(): void{
    this.validResult.emit(this.input.valid);
  }

}
