# TrsorerieApi

All URIs are relative to *https://api.federation-agricole.mg/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createCompte**](TrsorerieApi.md#createCompte) | **POST** /collectivites/{collectiviteId}/comptes | Créer un compte |
| [**deleteCompte**](TrsorerieApi.md#deleteCompte) | **DELETE** /collectivites/{collectiviteId}/comptes/{compteId} | Supprimer un compte |
| [**getCompte**](TrsorerieApi.md#getCompte) | **GET** /collectivites/{collectiviteId}/comptes/{compteId} | Obtenir un compte et son solde |
| [**getSituationTresorerie**](TrsorerieApi.md#getSituationTresorerie) | **GET** /collectivites/{collectiviteId}/tresorerie | Obtenir la situation de trésorerie globale |
| [**listComptes**](TrsorerieApi.md#listComptes) | **GET** /collectivites/{collectiviteId}/comptes | Lister les comptes d&#39;une collectivité |
| [**updateCompte**](TrsorerieApi.md#updateCompte) | **PATCH** /collectivites/{collectiviteId}/comptes/{compteId} | Mettre à jour un compte |


<a id="createCompte"></a>
# **createCompte**
> Compte createCompte(collectiviteId, compteCreate)

Créer un compte

Une collectivité ne peut avoir qu&#39;une seule caisse. Plusieurs comptes bancaires et mobile money sont autorisés. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TrsorerieApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    TrsorerieApi apiInstance = new TrsorerieApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    CompteCreate compteCreate = new CompteCreate(); // CompteCreate | 
    try {
      Compte result = apiInstance.createCompte(collectiviteId, compteCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TrsorerieApi#createCompte");
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
| **compteCreate** | [**CompteCreate**](CompteCreate.md)|  | |

### Return type

[**Compte**](Compte.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Compte créé |  -  |
| **404** | Ressource introuvable |  -  |
| **422** | Entité non traitable — règles métier non respectées |  -  |

<a id="deleteCompte"></a>
# **deleteCompte**
> deleteCompte(collectiviteId, compteId)

Supprimer un compte

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TrsorerieApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    TrsorerieApi apiInstance = new TrsorerieApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer compteId = 56; // Integer | 
    try {
      apiInstance.deleteCompte(collectiviteId, compteId);
    } catch (ApiException e) {
      System.err.println("Exception when calling TrsorerieApi#deleteCompte");
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
| **compteId** | **Integer**|  | |

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
| **204** | Compte supprimé |  -  |
| **404** | Ressource introuvable |  -  |
| **422** | Entité non traitable — règles métier non respectées |  -  |

<a id="getCompte"></a>
# **getCompte**
> Compte getCompte(collectiviteId, compteId)

Obtenir un compte et son solde

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TrsorerieApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    TrsorerieApi apiInstance = new TrsorerieApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer compteId = 56; // Integer | 
    try {
      Compte result = apiInstance.getCompte(collectiviteId, compteId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TrsorerieApi#getCompte");
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
| **compteId** | **Integer**|  | |

### Return type

[**Compte**](Compte.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Détail du compte avec solde |  -  |
| **404** | Ressource introuvable |  -  |

<a id="getSituationTresorerie"></a>
# **getSituationTresorerie**
> SituationTresorerie getSituationTresorerie(collectiviteId)

Obtenir la situation de trésorerie globale

Retourne le solde consolidé de tous les comptes de la collectivité.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TrsorerieApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    TrsorerieApi apiInstance = new TrsorerieApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    try {
      SituationTresorerie result = apiInstance.getSituationTresorerie(collectiviteId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TrsorerieApi#getSituationTresorerie");
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

[**SituationTresorerie**](SituationTresorerie.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Situation de trésorerie |  -  |
| **404** | Ressource introuvable |  -  |

<a id="listComptes"></a>
# **listComptes**
> List&lt;Compte&gt; listComptes(collectiviteId, type)

Lister les comptes d&#39;une collectivité

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TrsorerieApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    TrsorerieApi apiInstance = new TrsorerieApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    String type = "caisse"; // String | 
    try {
      List<Compte> result = apiInstance.listComptes(collectiviteId, type);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TrsorerieApi#listComptes");
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
| **type** | **String**|  | [optional] [enum: caisse, bancaire, mobile_money] |

### Return type

[**List&lt;Compte&gt;**](Compte.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des comptes |  -  |
| **404** | Ressource introuvable |  -  |

<a id="updateCompte"></a>
# **updateCompte**
> Compte updateCompte(collectiviteId, compteId, compteUpdate)

Mettre à jour un compte

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TrsorerieApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    TrsorerieApi apiInstance = new TrsorerieApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer compteId = 56; // Integer | 
    CompteUpdate compteUpdate = new CompteUpdate(); // CompteUpdate | 
    try {
      Compte result = apiInstance.updateCompte(collectiviteId, compteId, compteUpdate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TrsorerieApi#updateCompte");
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
| **compteId** | **Integer**|  | |
| **compteUpdate** | [**CompteUpdate**](CompteUpdate.md)|  | |

### Return type

[**Compte**](Compte.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Compte mis à jour |  -  |
| **404** | Ressource introuvable |  -  |

