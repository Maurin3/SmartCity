﻿using System;
using System.Collections.Generic;

namespace Model
{
    public partial class AspNetUserTokens
    {
        public string UserId { get; set; }
        public string LoginProvider { get; set; }
        public string Name { get; set; }
        public string Value { get; set; }

        public AspNetUsers User { get; set; }
    }
}