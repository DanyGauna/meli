# API Rest con servicios para detectar si un adn es mutante. Desarrollado por Daniel Gauna.

# Repositorio github:

URL: https://github.com/DanyGauna/meli

# Instrucciones de uso:

La API tiene 3 servicios:

# Servicio: /mutant
Recibe como parámetro una array de string que reprentan el dna a verificar.

URL: https://sublime-sunspot-341922.rj.r.appspot.com/mutantes/mutant

Método: POST

Header: Content-Type value application/json

Ejemplo de formato de cada tipo de dna:

MUTANTE: 
{
   "dna":[
      "ATGCGA",
      "CAGTGC",
      "TTATGT",
      "AGAAGG",
      "CCCCTA",
      "TCACTG"
   ]
}

NO MUTANTE: 
{
   "dna":[
      "ATGCGA",
      "CAGTGC",
      "TTATTT",
      "AGACGG",
      "GCGTCA",
      "TCACTG"
   ]
}

CON CARACTERES NO PERMITIDOS: 
{
   "dna":[
      "APGCGA",
      "CAGTGC",
      "TTATTT",
      "AGACGG",
      "GCGTCA",
      "TCACTG"
   ]
}

Los caracteres permitidos son: A, T, C, G.

Respuestas posibles:

200 OK: la dna es mutante.
403 FORBIDDEN: la dna no es mutante.
400 BAD_REQUEST: dna con caracteres no permitidos.

# Servicio: /stats
El método no recibe parámetros y devuelve un response en formato json con los valores: cantidad de dna mutantes, cantidad de dna no mutantes y la proporción.

URL: https://sublime-sunspot-341922.rj.r.appspot.com/mutantes/stats

Method: GET

Ejemplo de respuesta:

{
    "count_mutant_dna": 1,
    "count_human_dna": 1,
    "ratio": 1.0
}

# Servicio: /version

Método que solo devuelve la versión

https://sublime-sunspot-341922.rj.r.appspot.com/version

Respuesta: Versión: 1.0.0
_____________________________________________________________________________________________________________________________________________________________

# Base de Datos:

En la nube existe la instancia llamada meli y la base llamada mutants.

NOTA: no es necesario correr el script de la creacion de la tabla ya que el el sistema la creará si no existe.

En la carpeta /resource/scripts se encuentran dos archivos para la creación de la base de datos en caso de necesitar hacer pruebas locales.
Los archivos son:
create_schema.sql: para la creacion de la base de datos
create_table_dnas: para la creacion de la tabla dnas


