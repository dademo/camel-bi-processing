package fr.dademo.bi.companies.services.exception;

public final class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -9124728646951714514L;

    private ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ResourceNotFoundException notFoundDataSet(String dataSetName) {
        return notFoundDataSet(dataSetName, null);
    }

    public static ResourceNotFoundException notFoundDataSet(String dataSetName, Throwable cause) {
        return new ResourceNotFoundException(String.format("Data set %s was not found", dataSetName), cause);
    }

    public static ResourceNotFoundException notFoundByResourceTitle(String dataSetName, String resourceTitle) {
        return notFoundByResourceTitle(dataSetName, resourceTitle, null);
    }

    public static ResourceNotFoundException notFoundByResourceTitle(String dataSetName, String resourceTitle, Throwable cause) {
        return new ResourceNotFoundException(String.format("Resource `%s` of dataset `%s` was not found", resourceTitle, dataSetName), cause);
    }

    public static ResourceNotFoundException notFoundByResourceUrl(String dataSetName, String resourceUrl) {
        return notFoundByResourceUrl(dataSetName, resourceUrl, null);
    }

    public static ResourceNotFoundException notFoundByResourceUrl(String dataSetName, String resourceUrl, Throwable cause) {
        return new ResourceNotFoundException(String.format("Resource at url `%s` of dataset `%s` was not found", resourceUrl, dataSetName), cause);
    }
}
