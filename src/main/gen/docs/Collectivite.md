

# Collectivite


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** |  |  [optional] [readonly] |
|**numero** | **String** | Numéro unique de la collectivité |  [optional] |
|**nom** | **String** | Nom unique de la collectivité |  [optional] |
|**ville** | **String** |  |  [optional] |
|**specialite** | **String** | Spécialité agricole |  [optional] |
|**dateCreation** | **LocalDate** |  |  [optional] |
|**statut** | [**StatutEnum**](#StatutEnum) |  |  [optional] [readonly] |
|**federationId** | **Integer** |  |  [optional] [readonly] |



## Enum: StatutEnum

| Name | Value |
|---- | -----|
| EN_ATTENTE | &quot;en_attente&quot; |
| AUTORISEE | &quot;autorisee&quot; |
| REFUSEE | &quot;refusee&quot; |



