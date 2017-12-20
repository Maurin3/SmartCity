using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    public partial class UserInfo
    {
        public decimal Id { get; set; }
        public string LastName { get; set; }
        public string FirstName { get; set; }
        public DateTime Birthdate { get; set; }
        public string IdAspNetUser { get; set; }

        public AspNetUsers IdAspNetUserNavigation { get; set; }
    }
}
