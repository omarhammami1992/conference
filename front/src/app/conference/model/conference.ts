export interface Conference {
  name: string;
  link: string;
  address: Address;
  price: number;
  startDate: Date;
  endDate: Date;
  priceRanges?: PriceRange[];
  priceAttendingDays?: PriceAttendingDay[];
}

export interface PriceRange {

}

export interface PriceAttendingDay {}

export interface Address {
  city: string;
  country: string;
  latitude: string;
  longitude: string;
  fullAddress: string;
}

export interface Address {
  city: string;
  country: string;
  latitude: string;
  longitude: string;
  fullAddress: string;
}
