

# Activite


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** |  |  [optional] [readonly] |
|**titre** | **String** |  |  [optional] |
|**type** | **TypeActiviteEnum** |  |  [optional] |
|**dateActivite** | **LocalDate** |  |  [optional] |
|**obligatoire** | **Boolean** |  |  [optional] |
|**cible** | [**CibleEnum**](#CibleEnum) | Membres concernés par l&#39;obligation de présence |  [optional] |
|**collectiviteId** | **Integer** |  |  [optional] |
|**federationId** | **Integer** |  |  [optional] |



## Enum: CibleEnum

| Name | Value |
|---- | -----|
| TOUS | &quot;tous&quot; |
| JUNIORS | &quot;juniors&quot; |
| CONFIRMES | &quot;confirmes&quot; |



