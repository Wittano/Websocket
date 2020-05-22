export class User {
  username: string;
  password: string;
  email: string;
  gender: string;
  birthday: Date | string;

  constructor(
    name: string,
    password: string,
    email: string = '',
    gender: string = '',
    birthday: Date | string = new Date(new Date().getTime())
  ) {
    this.username = name;
    this.password = password;
    this.email = email;
    this.gender = gender;
    this.birthday = birthday;
  }
}
