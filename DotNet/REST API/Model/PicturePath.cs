using System;
using System.Collections.Generic;

namespace Model
{
    public partial class PicturePath
    {
        public decimal Id { get; set; }
        public string PicturePath1 { get; set; }

        public Room IdNavigation { get; set; }
    }
}
