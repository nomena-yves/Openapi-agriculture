

# Membre


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** |  |  [optional] [readonly] |
|**nom** | **String** |  |  [optional] |
|**prenom** | **String** |  |  [optional] |
|**dateNaissance** | **LocalDate** |  |  [optional] |
|**genre** | [**GenreEnum**](#GenreEnum) |  |  [optional] |
|**adresse** | **String** |  |  [optional] |
|**metier** | **String** |  |  [optional] |
|**telephone** | **String** |  |  [optional] |
|**email** | **String** |  |  [optional] |
|**dateAdhesion** | **LocalDate** |  |  [optional] [readonly] |
|**poste** | **PosteEnum** |  |  [optional] |
|**statut** | [**StatutEnum**](#StatutEnum) |  |  [optional] [readonly] |
|**parrainId** | **Integer** |  |  [optional] [readonly] |



## Enum: GenreEnum

| Name | Value |
|---- | -----|
| MASCULIN | &quot;masculin&quot; |
| FEMININ | &quot;feminin&quot; |



## Enum: StatutEnum

| Name | Value |
|---- | -----|
| ACTIF | &quot;actif&quot; |
| DEMISSIONNE | &quot;demissionne&quot; |



