using System;
using System.Collections.Generic;

namespace Model
{
    public partial class Room
    {
        public Room()
        {
            Lease = new HashSet<Lease>();
            Note = new HashSet<Note>();
            PicturePath = new HashSet<PicturePath>();
        }

        public decimal Id { get; set; }
        public decimal Surface { get; set; }
        public bool IsDesignedForReducedMobility { get; set; }
        public decimal MonthlyPriceWithAllCharges { get; set; }
        public decimal MonthlyPriceNoChargesIncluded { get; set; }
        public bool HasPrivateBathroom { get; set; }
        public bool HasPrivateKitchen { get; set; }
        public bool HasEquippedKitchen { get; set; }
        public string Street { get; set; }
        public string NumberOfTheHouse { get; set; }
        public decimal PostCode { get; set; }
        public string CityName { get; set; }

        public Building Building { get; set; }
        public ICollection<Lease> Lease { get; set; }
        public ICollection<Note> Note { get; set; }
        public ICollection<PicturePath> PicturePath { get; set; }
    }
}
