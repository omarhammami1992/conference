import {PricingMode} from "./pricing-mode";

export interface Conference {
  name: string;
  link: string;
  price: number;
  startDate: Date;
  endDate: Date;
}
