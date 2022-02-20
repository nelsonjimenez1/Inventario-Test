import { Role } from "./role";

export class UserDB {

    public id:number;
    public user:string;
    public password:string;
    public name:string;
    public age:number;
    public role:Role;
    public admissionDate:Date;

    constructor(id:number, user:string, password:string, name:string, age:number, role:Role, admissionDate:Date) {
      this.id = id;
      this.user = user;
      this.password = password;
      this.name = name;
      this.age = age;
      this.role = role;
      this.admissionDate = admissionDate;
  }
}
