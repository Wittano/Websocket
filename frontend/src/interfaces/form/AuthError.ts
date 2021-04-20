interface FieldsError {
  login: string;
  password: string;
}

export default interface AuthError {
  login: string;
  register: string;
  fields: FieldsError;
}
