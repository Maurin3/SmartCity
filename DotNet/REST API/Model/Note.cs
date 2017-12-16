using System;
using System.Collections.Generic;

namespace Model
{
    public partial class Note
    {
        public decimal Id { get; set; }
        public decimal Evaluation { get; set; }
        public string EvaluationTitle { get; set; }
        public string EvaluationText { get; set; }
        public decimal IdRoom { get; set; }
        public string UserId { get; set; }

        public Room IdRoomNavigation { get; set; }
        public AspNetUsers User { get; set; }
    }
}
