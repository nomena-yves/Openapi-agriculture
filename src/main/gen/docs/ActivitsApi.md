# ActivitsApi

All URIs are relative to *https://api.federation-agricole.mg/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createActivite**](ActivitsApi.md#createActivite) | **POST** /collectivites/{collectiviteId}/activites | Créer une activité exceptionnelle |
| [**createActiviteFederation**](ActivitsApi.md#createActiviteFederation) | **POST** /federation/activites | Créer une activité fédérale |
| [**deleteActivite**](ActivitsApi.md#deleteActivite) | **DELETE** /collectivites/{collectiviteId}/activites/{activiteId} | Supprimer une activité |
| [**getActivite**](ActivitsApi.md#getActivite) | **GET** /collectivites/{collectiviteId}/activites/{activiteId} | Obtenir une activité |
| [**listActivites**](ActivitsApi.md#listActivites) | **GET** /collectivites/{collectiviteId}/activites | Lister les activités d&#39;une collectivité |
| [**listActivitesFederation**](ActivitsApi.md#listActivitesFederation) | **GET** /federation/activites | Lister les activités planifiées par la fédération |
| [**updateActivite**](ActivitsApi.md#updateActivite) | **PATCH** /collectivites/{collectiviteId}/activites/{activiteId} | Mettre à jour une activité |


<a id="createActivite"></a>
# **createActivite**
> Activite createActivite(collectiviteId, activiteCreate)

Créer une activité exceptionnelle

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ActivitsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    ActivitsApi apiInstance = new ActivitsApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    ActiviteCreate activiteCreate = new ActiviteCreate(); // ActiviteCreate | 
    try {
      Activite result = apiInstance.createActivite(collectiviteId, activiteCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ActivitsApi#createActivite");
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
| **activiteCreate** | [**ActiviteCreate**](ActiviteCreate.md)|  | |

### Return type

[**Activite**](Activite.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Activité créée |  -  |
| **404** | Ressource introuvable |  -  |
| **422** | Entité non traitable — règles métier non respectées |  -  |

<a id="createActiviteFederation"></a>
# **createActiviteFederation**
> Activite createActiviteFederation(activiteCreate)

Créer une activité fédérale

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ActivitsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    ActivitsApi apiInstance = new ActivitsApi(defaultClient);
    ActiviteCreate activiteCreate = new ActiviteCreate(); // ActiviteCreate | 
    try {
      Activite result = apiInstance.createActiviteFederation(activiteCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ActivitsApi#createActiviteFederation");
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
| **activiteCreate** | [**ActiviteCreate**](ActiviteCreate.md)|  | |

### Return type

[**Activite**](Activite.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Activité fédérale créée |  -  |
| **422** | Entité non traitable — règles métier non respectées |  -  |

<a id="deleteActivite"></a>
# **deleteActivite**
> deleteActivite(collectiviteId, activiteId)

Supprimer une activité

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ActivitsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    ActivitsApi apiInstance = new ActivitsApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer activiteId = 56; // Integer | Identifiant de l'activité
    try {
      apiInstance.deleteActivite(collectiviteId, activiteId);
    } catch (ApiException e) {
      System.err.println("Exception when calling ActivitsApi#deleteActivite");
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
| **activiteId** | **Integer**| Identifiant de l&#39;activité | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Activité supprimée |  -  |
| **404** | Ressource introuvable |  -  |

<a id="getActivite"></a>
# **getActivite**
> Activite getActivite(collectiviteId, activiteId)

Obtenir une activité

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ActivitsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    ActivitsApi apiInstance = new ActivitsApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer activiteId = 56; // Integer | Identifiant de l'activité
    try {
      Activite result = apiInstance.getActivite(collectiviteId, activiteId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ActivitsApi#getActivite");
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
| **activiteId** | **Integer**| Identifiant de l&#39;activité | |

### Return type

[**Activite**](Activite.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Détail d&#39;une activité |  -  |
| **404** | Ressource introuvable |  -  |

<a id="listActivites"></a>
# **listActivites**
> ListActivites200Response listActivites(collectiviteId, page, limit, type, dateDebut, dateFin)

Lister les activités d&#39;une collectivité

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ActivitsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    ActivitsApi apiInstance = new ActivitsApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer page = 1; // Integer | 
    Integer limit = 20; // Integer | 
    String type = "ag_mensuelle"; // String | 
    LocalDate dateDebut = LocalDate.now(); // LocalDate | 
    LocalDate dateFin = LocalDate.now(); // LocalDate | 
    try {
      ListActivites200Response result = apiInstance.listActivites(collectiviteId, page, limit, type, dateDebut, dateFin);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ActivitsApi#listActivites");
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
| **type** | **String**|  | [optional] [enum: ag_mensuelle, formation_juniors, exceptionnelle, federation] |
| **dateDebut** | **LocalDate**|  | [optional] |
| **dateFin** | **LocalDate**|  | [optional] |

### Return type

[**ListActivites200Response**](ListActivites200Response.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des activités |  -  |
| **404** | Ressource introuvable |  -  |

<a id="listActivitesFederation"></a>
# **listActivitesFederation**
> ListActivites200Response listActivitesFederation(page, limit)

Lister les activités planifiées par la fédération

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ActivitsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    ActivitsApi apiInstance = new ActivitsApi(defaultClient);
    Integer page = 1; // Integer | 
    Integer limit = 20; // Integer | 
    try {
      ListActivites200Response result = apiInstance.listActivitesFederation(page, limit);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ActivitsApi#listActivitesFederation");
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

### Return type

[**ListActivites200Response**](ListActivites200Response.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des activités fédérales |  -  |

<a id="updateActivite"></a>
# **updateActivite**
> Activite updateActivite(collectiviteId, activiteId, activiteUpdate)

Mettre à jour une activité

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ActivitsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    ActivitsApi apiInstance = new ActivitsApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer activiteId = 56; // Integer | Identifiant de l'activité
    ActiviteUpdate activiteUpdate = new ActiviteUpdate(); // ActiviteUpdate | 
    try {
      Activite result = apiInstance.updateActivite(collectiviteId, activiteId, activiteUpdate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ActivitsApi#updateActivite");
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
| **activiteId** | **Integer**| Identifiant de l&#39;activité | |
| **activiteUpdate** | [**ActiviteUpdate**](ActiviteUpdate.md)|  | |

### Return type

[**Activite**](Activite.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Activité mise à jour |  -  |
| **404** | Ressource introuvable |  -  |

