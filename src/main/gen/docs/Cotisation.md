

# Cotisation


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** |  |  [optional] [readonly] |
|**membreId** | **Integer** |  |  [optional] |
|**collectiviteId** | **Integer** |  |  [optional] [readonly] |
|**type** | [**TypeEnum**](#TypeEnum) |  |  [optional] |
|**periodicite** | [**PeriodiciteEnum**](#PeriodiciteEnum) | Renseigné uniquement si type est périodique |  [optional] |
|**montant** | **BigDecimal** | Montant en MGA |  [optional] |
|**dateEcheance** | **LocalDate** |  |  [optional] |
|**montantEncaisse** | **BigDecimal** |  |  [optional] [readonly] |
|**montantRestant** | **BigDecimal** |  |  [optional] [readonly] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| MENSUELLE | &quot;mensuelle&quot; |
| ANNUELLE | &quot;annuelle&quot; |
| PONCTUELLE | &quot;ponctuelle&quot; |



## Enum: PeriodiciteEnum

| Name | Value |
|---- | -----|
| MENSUELLE | &quot;mensuelle&quot; |
| ANNUELLE | &quot;annuelle&quot; |



