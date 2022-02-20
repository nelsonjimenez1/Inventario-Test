import { UserDB } from "./userdb";

export class Product {
    public id:number;
    public name:string;
    public amount:number;
    public admissionDate:Date;
    public userDB:UserDB;
    

    constructor(id:number, name:string, amount:number, admissionDate:Date, userDB:UserDB) {
      this.id = id;
      this.name = name;
      this.amount = amount;
      this.admissionDate = admissionDate;
      this.userDB = userDB;
  }
}
