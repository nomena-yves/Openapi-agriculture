# CotisationsApi

All URIs are relative to *https://api.federation-agricole.mg/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createCotisation**](CotisationsApi.md#createCotisation) | **POST** /collectivites/{collectiviteId}/cotisations | Créer une cotisation |
| [**createEncaissement**](CotisationsApi.md#createEncaissement) | **POST** /collectivites/{collectiviteId}/cotisations/{cotisationId}/encaissements | Enregistrer un encaissement |
| [**listCotisations**](CotisationsApi.md#listCotisations) | **GET** /collectivites/{collectiviteId}/cotisations | Lister les cotisations d&#39;une collectivité |
| [**listEncaissements**](CotisationsApi.md#listEncaissements) | **GET** /collectivites/{collectiviteId}/cotisations/{cotisationId}/encaissements | Lister les encaissements d&#39;une cotisation |


<a id="createCotisation"></a>
# **createCotisation**
> Cotisation createCotisation(collectiviteId, cotisationCreate)

Créer une cotisation

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CotisationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    CotisationsApi apiInstance = new CotisationsApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    CotisationCreate cotisationCreate = new CotisationCreate(); // CotisationCreate | 
    try {
      Cotisation result = apiInstance.createCotisation(collectiviteId, cotisationCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CotisationsApi#createCotisation");
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
| **cotisationCreate** | [**CotisationCreate**](CotisationCreate.md)|  | |

### Return type

[**Cotisation**](Cotisation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Cotisation créée |  -  |
| **404** | Ressource introuvable |  -  |
| **422** | Entité non traitable — règles métier non respectées |  -  |

<a id="createEncaissement"></a>
# **createEncaissement**
> Encaissement createEncaissement(collectiviteId, cotisationId, encaissementCreate)

Enregistrer un encaissement

Le trésorier enregistre un paiement reçu pour une cotisation.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CotisationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    CotisationsApi apiInstance = new CotisationsApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer cotisationId = 56; // Integer | 
    EncaissementCreate encaissementCreate = new EncaissementCreate(); // EncaissementCreate | 
    try {
      Encaissement result = apiInstance.createEncaissement(collectiviteId, cotisationId, encaissementCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CotisationsApi#createEncaissement");
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
| **cotisationId** | **Integer**|  | |
| **encaissementCreate** | [**EncaissementCreate**](EncaissementCreate.md)|  | |

### Return type

[**Encaissement**](Encaissement.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Encaissement enregistré |  -  |
| **404** | Ressource introuvable |  -  |
| **422** | Entité non traitable — règles métier non respectées |  -  |

<a id="listCotisations"></a>
# **listCotisations**
> ListCotisations200Response listCotisations(collectiviteId, page, limit, membreId, type, dateDebut, dateFin)

Lister les cotisations d&#39;une collectivité

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CotisationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    CotisationsApi apiInstance = new CotisationsApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer page = 1; // Integer | 
    Integer limit = 20; // Integer | 
    Integer membreId = 56; // Integer | 
    String type = "mensuelle"; // String | 
    LocalDate dateDebut = LocalDate.now(); // LocalDate | 
    LocalDate dateFin = LocalDate.now(); // LocalDate | 
    try {
      ListCotisations200Response result = apiInstance.listCotisations(collectiviteId, page, limit, membreId, type, dateDebut, dateFin);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CotisationsApi#listCotisations");
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
| **page** | **Integer**|  | [optional] [default to 1] |
| **limit** | **Integer**|  | [optional] [default to 20] |
| **membreId** | **Integer**|  | [optional] |
| **type** | **String**|  | [optional] [enum: mensuelle, annuelle, ponctuelle] |
| **dateDebut** | **LocalDate**|  | [optional] |
| **dateFin** | **LocalDate**|  | [optional] |

### Return type

[**ListCotisations200Response**](ListCotisations200Response.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des cotisations |  -  |
| **404** | Ressource introuvable |  -  |

<a id="listEncaissements"></a>
# **listEncaissements**
> List&lt;Encaissement&gt; listEncaissements(collectiviteId, cotisationId)

Lister les encaissements d&#39;une cotisation

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CotisationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    CotisationsApi apiInstance = new CotisationsApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer cotisationId = 56; // Integer | 
    try {
      List<Encaissement> result = apiInstance.listEncaissements(collectiviteId, cotisationId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CotisationsApi#listEncaissements");
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
| **cotisationId** | **Integer**|  | |

### Return type

[**List&lt;Encaissement&gt;**](Encaissement.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des encaissements |  -  |
| **404** | Ressource introuvable |  -  |

