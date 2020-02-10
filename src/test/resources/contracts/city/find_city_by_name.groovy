import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return city by name=Barcelona"

    request {
        url "/cities/Barcelona"
        method GET()
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body (
                cityName: "Barcelona",
                hospitals: []
        )
    }
}