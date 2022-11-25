CREATE TABLE Usuarios(
Carnet int NOT NULL,
Nombre varchar(50) NOT NULL,
Correo varchar(50) NOT NULL,
Contraseña varchar(15) NOT NULL,
Rol char NOT NULL
);

CREATE TABLE Reservas(
ID_reserva int NOT NULL,
Fecha varchar(50) NOT NULL,
HoraIni varchar(50) NOT NULL,
HoraFin varchar(50) NOT NULL,
Recurso varchar(5) NOT NULL,
Carnet int NOT NULL
);

CREATE TABLE Recursos(
ID_recurso varchar(5) NOT NULL,
Nombre varchar(50) NOT NULL,
Ubicacion varchar(50) NOT NULL,
Tipo varchar(20) NOT NULL,
Capacidad int NOT NULL,
ID_interno int NOT NULL,
Descripcion varchar(100),
Disponible bool NOT NULL
);

ALTER TABLE Usuarios ADD CONSTRAINT PK_Usuarios PRIMARY KEY(Carnet);
ALTER TABLE Reservas ADD CONSTRAINT PK_Reservas PRIMARY KEY(ID_reserva);
ALTER TABLE Recursos ADD CONSTRAINT PK_Recursos PRIMARY KEY(ID_recurso);
ALTER TABLE Usuarios ADD CONSTRAINT UK_Usuarios UNIQUE(Correo);
ALTER TABLE Reservas ADD CONSTRAINT FK_ReservasUsuarios
FOREIGN KEY(Carnet) REFERENCES Usuarios(Carnet);
ALTER TABLE Reservas ADD CONSTRAINT FK_ReservasRecursos
FOREIGN KEY(Recurso) REFERENCES Recursos(ID_recurso);




