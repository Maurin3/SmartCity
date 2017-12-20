using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace Model
{
    public partial class namikotDBContext : DbContext
    {
        public virtual DbSet<AspNetRoleClaims> AspNetRoleClaims { get; set; }
        public virtual DbSet<AspNetRoles> AspNetRoles { get; set; }
        public virtual DbSet<AspNetUserClaims> AspNetUserClaims { get; set; }
        public virtual DbSet<AspNetUserLogins> AspNetUserLogins { get; set; }
        public virtual DbSet<AspNetUserRoles> AspNetUserRoles { get; set; }
        public virtual DbSet<AspNetUsers> AspNetUsers { get; set; }
        public virtual DbSet<AspNetUserTokens> AspNetUserTokens { get; set; }
        public virtual DbSet<Building> Building { get; set; }
        public virtual DbSet<DeclarationConformity> DeclarationConformity { get; set; }
        public virtual DbSet<Lease> Lease { get; set; }
        public virtual DbSet<Note> Note { get; set; }
        public virtual DbSet<PicturePath> PicturePath { get; set; }
        public virtual DbSet<Room> Room { get; set; }
        public virtual DbSet<UserInfo> UserInfo { get; set; }

        public namikotDBContext(DbContextOptions<namikotDBContext> options) : base(options)
        {
            
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<AspNetRoleClaims>(entity =>
            {
                entity.ToTable("AspNetRoleClaims", "dbo");

                entity.HasIndex(e => e.RoleId);

                entity.Property(e => e.RoleId).IsRequired();

                entity.HasOne(d => d.Role)
                    .WithMany(p => p.AspNetRoleClaims)
                    .HasForeignKey(d => d.RoleId);
            });

            modelBuilder.Entity<AspNetRoles>(entity =>
            {
                entity.ToTable("AspNetRoles", "dbo");

                entity.HasIndex(e => e.NormalizedName)
                    .HasName("RoleNameIndex")
                    .IsUnique()
                    .HasFilter("([NormalizedName] IS NOT NULL)");

                entity.Property(e => e.Id).ValueGeneratedNever();

                entity.Property(e => e.Name).HasMaxLength(256);

                entity.Property(e => e.NormalizedName).HasMaxLength(256);
            });

            modelBuilder.Entity<AspNetUserClaims>(entity =>
            {
                entity.ToTable("AspNetUserClaims", "dbo");

                entity.HasIndex(e => e.UserId);

                entity.Property(e => e.UserId).IsRequired();

                entity.HasOne(d => d.User)
                    .WithMany(p => p.AspNetUserClaims)
                    .HasForeignKey(d => d.UserId);
            });

            modelBuilder.Entity<AspNetUserLogins>(entity =>
            {
                entity.HasKey(e => new { e.LoginProvider, e.ProviderKey });

                entity.ToTable("AspNetUserLogins", "dbo");

                entity.HasIndex(e => e.UserId);

                entity.Property(e => e.UserId).IsRequired();

                entity.HasOne(d => d.User)
                    .WithMany(p => p.AspNetUserLogins)
                    .HasForeignKey(d => d.UserId);
            });

            modelBuilder.Entity<AspNetUserRoles>(entity =>
            {
                entity.HasKey(e => new { e.UserId, e.RoleId });

                entity.ToTable("AspNetUserRoles", "dbo");

                entity.HasIndex(e => e.RoleId);

                entity.HasOne(d => d.Role)
                    .WithMany(p => p.AspNetUserRoles)
                    .HasForeignKey(d => d.RoleId);

                entity.HasOne(d => d.User)
                    .WithMany(p => p.AspNetUserRoles)
                    .HasForeignKey(d => d.UserId);
            });

            modelBuilder.Entity<AspNetUsers>(entity =>
            {
                entity.ToTable("AspNetUsers", "dbo");

                entity.HasIndex(e => e.NormalizedEmail)
                    .HasName("EmailIndex");

                entity.HasIndex(e => e.NormalizedUserName)
                    .HasName("UserNameIndex")
                    .IsUnique()
                    .HasFilter("([NormalizedUserName] IS NOT NULL)");

                entity.Property(e => e.Id).ValueGeneratedNever();

                entity.Property(e => e.Email).HasMaxLength(256);

                entity.Property(e => e.NormalizedEmail).HasMaxLength(256);

                entity.Property(e => e.NormalizedUserName).HasMaxLength(256);

                entity.Property(e => e.UserName).HasMaxLength(256);
            });

            modelBuilder.Entity<AspNetUserTokens>(entity =>
            {
                entity.HasKey(e => new { e.UserId, e.LoginProvider, e.Name });

                entity.ToTable("AspNetUserTokens", "dbo");

                entity.HasOne(d => d.User)
                    .WithMany(p => p.AspNetUserTokens)
                    .HasForeignKey(d => d.UserId);
            });

            modelBuilder.Entity<Building>(entity =>
            {
                entity.HasKey(e => new { e.Street, e.NumberOfTheHouse, e.PostCode, e.CityName });

                entity.ToTable("Building", "dbo");

                entity.Property(e => e.Street)
                    .HasColumnName("street")
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.NumberOfTheHouse)
                    .HasColumnName("numberOfTheHouse")
                    .HasMaxLength(3)
                    .IsUnicode(false);

                entity.Property(e => e.PostCode)
                    .HasColumnName("postCode")
                    .HasColumnType("numeric(4, 0)");

                entity.Property(e => e.CityName)
                    .HasColumnName("cityName")
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.FloorRoom)
                    .HasColumnName("floor_room")
                    .HasColumnType("numeric(2, 0)");

                entity.Property(e => e.Latitude)
                    .HasColumnName("latitude")
                    .HasColumnType("decimal(9, 6)");

                entity.Property(e => e.Longitude)
                    .HasColumnName("longitude")
                    .HasColumnType("decimal(9, 6)");
            });

            modelBuilder.Entity<DeclarationConformity>(entity =>
            {
                entity.ToTable("DeclarationConformity", "dbo");

                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("numeric(4, 0)")
                    .ValueGeneratedOnAdd();

                entity.Property(e => e.CityName)
                    .IsRequired()
                    .HasColumnName("cityName")
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.CorporatePurpose)
                    .IsRequired()
                    .HasColumnName("corporatePurpose")
                    .HasMaxLength(20)
                    .IsUnicode(false);

                entity.Property(e => e.DateRentalLicense)
                    .HasColumnName("dateRentalLicense")
                    .HasColumnType("date");

                entity.Property(e => e.ExceptionRentalLicense)
                    .HasColumnName("exceptionRentalLicense")
                    .HasMaxLength(20)
                    .IsUnicode(false);

                entity.Property(e => e.IndividualHousing)
                    .HasColumnName("individualHousing")
                    .HasColumnType("numeric(2, 0)");

                entity.Property(e => e.IndividualHousingLess28SqMeter)
                    .HasColumnName("individualHousingLess28SqMeter")
                    .HasColumnType("numeric(2, 0)");

                entity.Property(e => e.IndividualUnitWithCollectiveDwelling)
                    .HasColumnName("individualUnitWithCollectiveDwelling")
                    .HasColumnType("numeric(20, 0)");

                entity.Property(e => e.IsInOrder).HasColumnName("isInOrder");

                entity.Property(e => e.NotInformedWithRentalObligation).HasColumnName("notInformedWithRentalObligation");

                entity.Property(e => e.NotRentingLocation).HasColumnName("notRentingLocation");

                entity.Property(e => e.NumberOfTheHouse)
                    .IsRequired()
                    .HasColumnName("numberOfTheHouse")
                    .HasMaxLength(3)
                    .IsUnicode(false);

                entity.Property(e => e.PartForHousingWithLess28SqMeter)
                    .HasColumnName("partForHousingWithLess28SqMeter")
                    .HasColumnType("numeric(2, 0)");

                entity.Property(e => e.PostCode)
                    .HasColumnName("postCode")
                    .HasColumnType("numeric(4, 0)");

                entity.Property(e => e.ProductDivision)
                    .IsRequired()
                    .HasColumnName("productDivision")
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.ProductParcel)
                    .IsRequired()
                    .HasColumnName("productParcel")
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.ProductSection)
                    .IsRequired()
                    .HasColumnName("productSection")
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.ProfessionalPartForOwner)
                    .HasColumnName("professionalPartForOwner")
                    .HasColumnType("numeric(2, 0)");

                entity.Property(e => e.RentalLicense)
                    .HasColumnName("rentalLicense")
                    .HasMaxLength(20)
                    .IsUnicode(false);

                entity.Property(e => e.Residence)
                    .IsRequired()
                    .HasColumnName("residence")
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.Street)
                    .IsRequired()
                    .HasColumnName("street")
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.UnitForCommercialUsing)
                    .HasColumnName("unitForCommercialUsing")
                    .HasColumnType("numeric(2, 0)");

                entity.Property(e => e.UserId1)
                    .IsRequired()
                    .HasColumnName("userId1")
                    .HasMaxLength(450);

                entity.Property(e => e.UserId2)
                    .HasColumnName("userId2")
                    .HasMaxLength(450);

                entity.Property(e => e.WorkInProgress).HasColumnName("workInProgress");

                entity.HasOne(d => d.UserId1Navigation)
                    .WithMany(p => p.DeclarationConformityUserId1Navigation)
                    .HasForeignKey(d => d.UserId1)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Owner_fk");

                entity.HasOne(d => d.UserId2Navigation)
                    .WithMany(p => p.DeclarationConformityUserId2Navigation)
                    .HasForeignKey(d => d.UserId2)
                    .HasConstraintName("Admin_fk");
            });

            modelBuilder.Entity<Lease>(entity =>
            {
                entity.ToTable("Lease", "dbo");

                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("numeric(5, 0)")
                    .ValueGeneratedOnAdd();

                entity.Property(e => e.AmountCharges)
                    .HasColumnName("amountCharges")
                    .HasColumnType("numeric(6, 0)");

                entity.Property(e => e.CanBeCanceled).HasColumnName("canBeCanceled");

                entity.Property(e => e.DurationInMonths)
                    .HasColumnName("durationInMonths")
                    .HasColumnType("numeric(2, 0)");

                entity.Property(e => e.IdRoom)
                    .HasColumnName("idRoom")
                    .HasColumnType("numeric(5, 0)");

                entity.Property(e => e.LeaseRegistration)
                    .IsRequired()
                    .HasColumnName("leaseRegistration")
                    .HasMaxLength(10)
                    .IsUnicode(false);

                entity.Property(e => e.ObjectOfLease)
                    .IsRequired()
                    .HasColumnName("objectOfLease")
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.RentAmount)
                    .HasColumnName("rentAmount")
                    .HasColumnType("numeric(6, 0)");

                entity.Property(e => e.RentGuarrantee)
                    .HasColumnName("rentGuarrantee")
                    .HasColumnType("numeric(5, 0)");

                entity.Property(e => e.Rules)
                    .HasColumnName("rules")
                    .HasMaxLength(300)
                    .IsUnicode(false);

                entity.HasOne(d => d.IdRoomNavigation)
                    .WithMany(p => p.Lease)
                    .HasForeignKey(d => d.IdRoom)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Room_fk3");
            });

            modelBuilder.Entity<Note>(entity =>
            {
                entity.ToTable("Note", "dbo");

                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("numeric(6, 0)")
                    .ValueGeneratedOnAdd();

                entity.Property(e => e.Evaluation)
                    .HasColumnName("evaluation")
                    .HasColumnType("numeric(1, 0)");

                entity.Property(e => e.EvaluationText)
                    .IsRequired()
                    .HasColumnName("evaluationText")
                    .HasMaxLength(300)
                    .IsUnicode(false);

                entity.Property(e => e.EvaluationTitle)
                    .IsRequired()
                    .HasColumnName("evaluationTitle")
                    .HasMaxLength(30)
                    .IsUnicode(false);

                entity.Property(e => e.IdRoom)
                    .HasColumnName("idRoom")
                    .HasColumnType("numeric(5, 0)");

                entity.Property(e => e.UserId)
                    .IsRequired()
                    .HasColumnName("userId")
                    .HasMaxLength(450);

                entity.HasOne(d => d.IdRoomNavigation)
                    .WithMany(p => p.Note)
                    .HasForeignKey(d => d.IdRoom)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Room_fk1");

                entity.HasOne(d => d.User)
                    .WithMany(p => p.Note)
                    .HasForeignKey(d => d.UserId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("User_fk");
            });

            modelBuilder.Entity<PicturePath>(entity =>
            {
                entity.HasKey(e => new { e.Id, e.PicturePath1 });

                entity.ToTable("PicturePath", "dbo");

                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("numeric(5, 0)")
                    .ValueGeneratedOnAdd();

                entity.Property(e => e.PicturePath1)
                    .HasColumnName("picturePath")
                    .HasMaxLength(30)
                    .IsUnicode(false);

                entity.HasOne(d => d.IdNavigation)
                    .WithMany(p => p.PicturePath)
                    .HasForeignKey(d => d.Id)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Room_fk2");
            });

            modelBuilder.Entity<Room>(entity =>
            {
                entity.ToTable("Room", "dbo");

                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("numeric(5, 0)")
                    .ValueGeneratedOnAdd();

                entity.Property(e => e.CityName)
                    .IsRequired()
                    .HasColumnName("cityName")
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.HasEquippedKitchen).HasColumnName("hasEquippedKitchen");

                entity.Property(e => e.HasPrivateBathroom).HasColumnName("hasPrivateBathroom");

                entity.Property(e => e.HasPrivateKitchen).HasColumnName("hasPrivateKitchen");

                entity.Property(e => e.IsDesignedForReducedMobility).HasColumnName("isDesignedForReducedMobility");

                entity.Property(e => e.MonthlyPriceNoChargesIncluded)
                    .HasColumnName("monthlyPriceNoChargesIncluded")
                    .HasColumnType("numeric(5, 0)");

                entity.Property(e => e.MonthlyPriceWithAllCharges)
                    .HasColumnName("monthlyPriceWithAllCharges")
                    .HasColumnType("numeric(5, 0)");

                entity.Property(e => e.NumberOfTheHouse)
                    .IsRequired()
                    .HasColumnName("numberOfTheHouse")
                    .HasMaxLength(3)
                    .IsUnicode(false);

                entity.Property(e => e.PostCode)
                    .HasColumnName("postCode")
                    .HasColumnType("numeric(4, 0)");

                entity.Property(e => e.Street)
                    .IsRequired()
                    .HasColumnName("street")
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.Surface)
                    .HasColumnName("surface")
                    .HasColumnType("numeric(3, 0)");

                entity.HasOne(d => d.Building)
                    .WithMany(p => p.Room)
                    .HasForeignKey(d => new { d.Street, d.NumberOfTheHouse, d.PostCode, d.CityName })
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Building_fk");
            });

            modelBuilder.Entity<UserInfo>(entity =>
            {
                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("numeric(5, 0)")
                    .ValueGeneratedOnAdd();

                entity.Property(e => e.Birthdate)
                    .HasColumnName("birthdate")
                    .HasColumnType("datetime");

                entity.Property(e => e.FirstName)
                    .IsRequired()
                    .HasColumnName("firstName")
                    .HasMaxLength(250);

                entity.Property(e => e.IdAspNetUser)
                    .IsRequired()
                    .HasColumnName("idAspNetUser")
                    .HasMaxLength(450);

                entity.Property(e => e.LastName)
                    .IsRequired()
                    .HasColumnName("lastName")
                    .HasMaxLength(250);

                entity.HasOne(d => d.IdAspNetUserNavigation)
                    .WithMany(p => p.UserInfo)
                    .HasForeignKey(d => d.IdAspNetUser)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("AspNetUser_fk");
            });
        }
    }
}
