using System;
using System.Collections.Generic;

namespace Model
{
    public partial class Lease
    {
        public string ObjectOfLease { get; set; }
        public decimal Id { get; set; }
        public decimal IdRoom { get; set; }
        public decimal DurationInMonths { get; set; }
        public decimal RentAmount { get; set; }
        public decimal RentGuarrantee { get; set; }
        public decimal AmountCharges { get; set; }
        public string LeaseRegistration { get; set; }
        public string Rules { get; set; }
        public bool CanBeCanceled { get; set; }

        public Room IdRoomNavigation { get; set; }
    }
}
