import { Role } from "./role";

export class UserDB {

    public id:number;
    public name:string;
    public role:Role;
    public active:boolean;

    constructor(id:number, name:string, role:Role, active:boolean) {
      this.id = id;
      this.name = name;
      this.role = role;
      this.active = active;
  }
}
