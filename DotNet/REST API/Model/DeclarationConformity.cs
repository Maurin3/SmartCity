using System;
using System.Collections.Generic;

namespace Model
{
    public partial class DeclarationConformity
    {
        public decimal Id { get; set; }
        public bool? IsInOrder { get; set; }
        public string ProductDivision { get; set; }
        public string ProductSection { get; set; }
        public string ProductParcel { get; set; }
        public string CorporatePurpose { get; set; }
        public string Residence { get; set; }
        public decimal IndividualHousing { get; set; }
        public decimal IndividualHousingLess28SqMeter { get; set; }
        public decimal IndividualUnitWithCollectiveDwelling { get; set; }
        public decimal UnitForCommercialUsing { get; set; }
        public decimal ProfessionalPartForOwner { get; set; }
        public decimal PartForHousingWithLess28SqMeter { get; set; }
        public string RentalLicense { get; set; }
        public DateTime? DateRentalLicense { get; set; }
        public string ExceptionRentalLicense { get; set; }
        public bool? NotInformedWithRentalObligation { get; set; }
        public bool? NotRentingLocation { get; set; }
        public bool? WorkInProgress { get; set; }
        public string Street { get; set; }
        public string NumberOfTheHouse { get; set; }
        public decimal PostCode { get; set; }
        public string CityName { get; set; }
        public string UserId1 { get; set; }
        public string UserId2 { get; set; }

        public AspNetUsers UserId1Navigation { get; set; }
        public AspNetUsers UserId2Navigation { get; set; }
    }
}
