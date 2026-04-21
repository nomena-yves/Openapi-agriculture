

# MembreAdmission


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**nom** | **String** |  |  |
|**prenom** | **String** |  |  |
|**dateNaissance** | **LocalDate** |  |  |
|**genre** | [**GenreEnum**](#GenreEnum) |  |  |
|**adresse** | **String** |  |  |
|**metier** | **String** |  |  |
|**telephone** | **String** |  |  |
|**email** | **String** |  |  |
|**parrainId** | **Integer** | ID d&#39;un membre confirmé avec plus de 90 jours d&#39;ancienneté |  |
|**modePaiementAdhesion** | [**ModePaiementAdhesionEnum**](#ModePaiementAdhesionEnum) | Frais d&#39;adhésion de 50 000 MGA |  |



## Enum: GenreEnum

| Name | Value |
|---- | -----|
| MASCULIN | &quot;masculin&quot; |
| FEMININ | &quot;feminin&quot; |



## Enum: ModePaiementAdhesionEnum

| Name | Value |
|---- | -----|
| MOBILE_MONEY | &quot;mobile_money&quot; |
| VIREMENT_BANCAIRE | &quot;virement_bancaire&quot; |



