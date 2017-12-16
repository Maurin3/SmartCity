using System;
using System.Collections.Generic;

namespace Model
{
    public partial class Building
    {
        public Building()
        {
            Room = new HashSet<Room>();
        }

        public string Street { get; set; }
        public string NumberOfTheHouse { get; set; }
        public decimal PostCode { get; set; }
        public string CityName { get; set; }
        public decimal FloorRoom { get; set; }
        public decimal Longitude { get; set; }
        public decimal Latitude { get; set; }

        public ICollection<Room> Room { get; set; }
    }
}
