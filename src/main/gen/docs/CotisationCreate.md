

# CotisationCreate


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**membreId** | **Integer** |  |  |
|**type** | [**TypeEnum**](#TypeEnum) |  |  |
|**periodicite** | [**PeriodiciteEnum**](#PeriodiciteEnum) |  |  [optional] |
|**montant** | **BigDecimal** |  |  |
|**dateEcheance** | **LocalDate** |  |  |
|**motif** | **String** | Obligatoire si type est ponctuelle |  [optional] |



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



