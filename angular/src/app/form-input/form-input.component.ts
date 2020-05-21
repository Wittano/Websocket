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

  @Output() inputValue: EventEmitter<string> = new EventEmitter<string>();
  @Output() validResult: EventEmitter<boolean> = new EventEmitter<boolean>();

  input: FormControl;
  validEmail: boolean;

  constructor() {}

  ngOnInit(): void {
    this.input = new FormControl('', [
      Validators.required,
      Validators.minLength(this.min),
      Validators.maxLength(this.max),
      Validators.pattern(this.pattern)
    ]);
  }

  valid(): void {
    // This code exist because I didn't know why Validations.patter(this.pattern) didn't work.
    // In future I may add better validation or will find out how dose it work
    if (this.type.toLowerCase() === 'email') {
      const regex: RegExp = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      this.validEmail = regex.test(this.input.value);
      this.validResult.emit(this.validEmail);
    } else {
      this.validResult.emit(this.input.valid);
    }

    this.inputValue.emit(this.input.value);
  }
}
