# StatistiquesApi

All URIs are relative to *https://api.federation-agricole.mg/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createRapportMensuel**](StatistiquesApi.md#createRapportMensuel) | **POST** /collectivites/{collectiviteId}/rapports/mensuel | Soumettre le rapport mensuel à la fédération |
| [**getStatsMembre**](StatistiquesApi.md#getStatsMembre) | **GET** /collectivites/{collectiviteId}/statistiques/membres/{membreId} | Statistiques détaillées d&#39;un membre |
| [**getStatsMembres**](StatistiquesApi.md#getStatsMembres) | **GET** /collectivites/{collectiviteId}/statistiques/membres | Statistiques détaillées des membres actifs |
| [**listRapportsMensuels**](StatistiquesApi.md#listRapportsMensuels) | **GET** /collectivites/{collectiviteId}/rapports/mensuel | Lister les rapports mensuels d&#39;une collectivité |


<a id="createRapportMensuel"></a>
# **createRapportMensuel**
> RapportMensuel createRapportMensuel(collectiviteId, rapportMensuelCreate)

Soumettre le rapport mensuel à la fédération

Le président soumet le taux d&#39;assiduité global et le nombre de membres inscrits pour le mois écoulé. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.StatistiquesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    StatistiquesApi apiInstance = new StatistiquesApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    RapportMensuelCreate rapportMensuelCreate = new RapportMensuelCreate(); // RapportMensuelCreate | 
    try {
      RapportMensuel result = apiInstance.createRapportMensuel(collectiviteId, rapportMensuelCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling StatistiquesApi#createRapportMensuel");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **collectiviteId** | **Integer**| Identifiant de la collectivité | |
| **rapportMensuelCreate** | [**RapportMensuelCreate**](RapportMensuelCreate.md)|  | |

### Return type

[**RapportMensuel**](RapportMensuel.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Rapport soumis |  -  |
| **404** | Ressource introuvable |  -  |
| **422** | Entité non traitable — règles métier non respectées |  -  |

<a id="getStatsMembre"></a>
# **getStatsMembre**
> StatsMembre getStatsMembre(collectiviteId, membreId, dateDebut, dateFin)

Statistiques détaillées d&#39;un membre

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.StatistiquesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    StatistiquesApi apiInstance = new StatistiquesApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer membreId = 56; // Integer | Identifiant du membre
    LocalDate dateDebut = LocalDate.now(); // LocalDate | 
    LocalDate dateFin = LocalDate.now(); // LocalDate | 
    try {
      StatsMembre result = apiInstance.getStatsMembre(collectiviteId, membreId, dateDebut, dateFin);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling StatistiquesApi#getStatsMembre");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **collectiviteId** | **Integer**| Identifiant de la collectivité | |
| **membreId** | **Integer**| Identifiant du membre | |
| **dateDebut** | **LocalDate**|  | |
| **dateFin** | **LocalDate**|  | |

### Return type

[**StatsMembre**](StatsMembre.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Statistiques du membre |  -  |
| **404** | Ressource introuvable |  -  |

<a id="getStatsMembres"></a>
# **getStatsMembres**
> List&lt;StatsMembre&gt; getStatsMembres(collectiviteId, dateDebut, dateFin)

Statistiques détaillées des membres actifs

Retourne pour chaque membre actif : taux d&#39;assiduité, montant encaissé et montant impayé potentiel sur une période donnée. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.StatistiquesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    StatistiquesApi apiInstance = new StatistiquesApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    LocalDate dateDebut = LocalDate.now(); // LocalDate | 
    LocalDate dateFin = LocalDate.now(); // LocalDate | 
    try {
      List<StatsMembre> result = apiInstance.getStatsMembres(collectiviteId, dateDebut, dateFin);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling StatistiquesApi#getStatsMembres");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **collectiviteId** | **Integer**| Identifiant de la collectivité | |
| **dateDebut** | **LocalDate**|  | |
| **dateFin** | **LocalDate**|  | |

### Return type

[**List&lt;StatsMembre&gt;**](StatsMembre.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Statistiques détaillées par membre |  -  |
| **400** | Requête invalide |  -  |
| **404** | Ressource introuvable |  -  |

<a id="listRapportsMensuels"></a>
# **listRapportsMensuels**
> List&lt;RapportMensuel&gt; listRapportsMensuels(collectiviteId, annee)

Lister les rapports mensuels d&#39;une collectivité

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.StatistiquesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    StatistiquesApi apiInstance = new StatistiquesApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer annee = 2025; // Integer | 
    try {
      List<RapportMensuel> result = apiInstance.listRapportsMensuels(collectiviteId, annee);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling StatistiquesApi#listRapportsMensuels");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **collectiviteId** | **Integer**| Identifiant de la collectivité | |
| **annee** | **Integer**|  | [optional] |

### Return type

[**List&lt;RapportMensuel&gt;**](RapportMensuel.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des rapports mensuels |  -  |
| **404** | Ressource introuvable |  -  |

