# CollectivitsApi

All URIs are relative to *https://api.federation-agricole.mg/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createCollectivite**](CollectivitsApi.md#createCollectivite) | **POST** /collectivites | Demander l&#39;ouverture d&#39;une nouvelle collectivité |
| [**getCollectivite**](CollectivitsApi.md#getCollectivite) | **GET** /collectivites/{collectiviteId} | Obtenir une collectivité |
| [**listCollectivites**](CollectivitsApi.md#listCollectivites) | **GET** /collectivites | Lister toutes les collectivités |
| [**setAutorisationCollectivite**](CollectivitsApi.md#setAutorisationCollectivite) | **PATCH** /collectivites/{collectiviteId}/autorisation | Accorder ou refuser l&#39;autorisation d&#39;ouverture |
| [**updateCollectivite**](CollectivitsApi.md#updateCollectivite) | **PATCH** /collectivites/{collectiviteId} | Mettre à jour une collectivité |


<a id="createCollectivite"></a>
# **createCollectivite**
> Collectivite createCollectivite(collectiviteCreate)

Demander l&#39;ouverture d&#39;une nouvelle collectivité

Soumet une demande d&#39;ouverture. La fédération doit ensuite accorder l&#39;autorisation formelle via PATCH /collectivites/{id}/autorisation. Conditions : au moins 10 membres avec 6 mois d&#39;ancienneté, postes spécifiques pourvus. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CollectivitsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    CollectivitsApi apiInstance = new CollectivitsApi(defaultClient);
    CollectiviteCreate collectiviteCreate = new CollectiviteCreate(); // CollectiviteCreate | 
    try {
      Collectivite result = apiInstance.createCollectivite(collectiviteCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CollectivitsApi#createCollectivite");
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
| **collectiviteCreate** | [**CollectiviteCreate**](CollectiviteCreate.md)|  | |

### Return type

[**Collectivite**](Collectivite.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Demande d&#39;ouverture créée |  -  |
| **400** | Requête invalide |  -  |
| **422** | Entité non traitable — règles métier non respectées |  -  |

<a id="getCollectivite"></a>
# **getCollectivite**
> Collectivite getCollectivite(collectiviteId)

Obtenir une collectivité

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CollectivitsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    CollectivitsApi apiInstance = new CollectivitsApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    try {
      Collectivite result = apiInstance.getCollectivite(collectiviteId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CollectivitsApi#getCollectivite");
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

### Return type

[**Collectivite**](Collectivite.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Détail d&#39;une collectivité |  -  |
| **404** | Ressource introuvable |  -  |

<a id="listCollectivites"></a>
# **listCollectivites**
> ListCollectivites200Response listCollectivites(page, limit, ville, specialite)

Lister toutes les collectivités

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CollectivitsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    CollectivitsApi apiInstance = new CollectivitsApi(defaultClient);
    Integer page = 1; // Integer | 
    Integer limit = 20; // Integer | 
    String ville = "ville_example"; // String | Filtrer par ville
    String specialite = "specialite_example"; // String | Filtrer par spécialité agricole
    try {
      ListCollectivites200Response result = apiInstance.listCollectivites(page, limit, ville, specialite);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CollectivitsApi#listCollectivites");
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
| **page** | **Integer**|  | [optional] [default to 1] |
| **limit** | **Integer**|  | [optional] [default to 20] |
| **ville** | **String**| Filtrer par ville | [optional] |
| **specialite** | **String**| Filtrer par spécialité agricole | [optional] |

### Return type

[**ListCollectivites200Response**](ListCollectivites200Response.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des collectivités |  -  |
| **400** | Requête invalide |  -  |

<a id="setAutorisationCollectivite"></a>
# **setAutorisationCollectivite**
> Collectivite setAutorisationCollectivite(collectiviteId, setAutorisationCollectiviteRequest)

Accorder ou refuser l&#39;autorisation d&#39;ouverture

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CollectivitsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    CollectivitsApi apiInstance = new CollectivitsApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    SetAutorisationCollectiviteRequest setAutorisationCollectiviteRequest = new SetAutorisationCollectiviteRequest(); // SetAutorisationCollectiviteRequest | 
    try {
      Collectivite result = apiInstance.setAutorisationCollectivite(collectiviteId, setAutorisationCollectiviteRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CollectivitsApi#setAutorisationCollectivite");
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
| **setAutorisationCollectiviteRequest** | [**SetAutorisationCollectiviteRequest**](SetAutorisationCollectiviteRequest.md)|  | |

### Return type

[**Collectivite**](Collectivite.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Statut d&#39;autorisation mis à jour |  -  |
| **404** | Ressource introuvable |  -  |

<a id="updateCollectivite"></a>
# **updateCollectivite**
> Collectivite updateCollectivite(collectiviteId, collectiviteUpdate)

Mettre à jour une collectivité

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CollectivitsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    CollectivitsApi apiInstance = new CollectivitsApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    CollectiviteUpdate collectiviteUpdate = new CollectiviteUpdate(); // CollectiviteUpdate | 
    try {
      Collectivite result = apiInstance.updateCollectivite(collectiviteId, collectiviteUpdate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CollectivitsApi#updateCollectivite");
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
| **collectiviteUpdate** | [**CollectiviteUpdate**](CollectiviteUpdate.md)|  | |

### Return type

[**Collectivite**](Collectivite.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Collectivité mise à jour |  -  |
| **404** | Ressource introuvable |  -  |

