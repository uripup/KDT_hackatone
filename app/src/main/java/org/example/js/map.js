var locPosition;
var container = document.getElementById('map'); // 지도를 담을 영역의 DOM 레퍼런스
var options = { // 지도를 생성할 때 필요한 기본 옵션
    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표.
    level: 3 // 지도의 레벨(확대, 축소 정도)
};
var map = new kakao.maps.Map(container, options);

if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function (position) {
        var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도

        var locPosition = new kakao.maps.LatLng(lat, lon); // 마커가 표시될 위치
        map.setCenter(locPosition);

        // 마커와 인포윈도우를 표시
        displayMarker(locPosition);
    });
} else {
    var locPosition = new kakao.maps.LatLng(33.450701, 126.570667);
    map.setCenter(locPosition);
    displayMarker(locPosition, 'geolocation을 사용할 수 없어요..');
}

function searchFunction() {
    let input = document.getElementById('searchInput').value.toLowerCase();

    var ps = new kakao.maps.services.Places(); // 장소 검색 객체 생성
    if(input != null){
        removeMarkers();
    }
    // 키워드로 장소 검색
    ps.keywordSearch(input, placesSearchCB);

    function placesSearchCB(data, status, pagination) {
        if (status === kakao.maps.services.Status.OK) {
            var bounds = new kakao.maps.LatLngBounds();

            for (var i = 0; i < data.length; i++) {
                displayMarker(data[i]);
                bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
            }

            if (pagination.hasNextPage) {
                pagination.nextPage();
            }
            // 지도 범위 재설정
            map.setBounds(bounds);
        }
    }
}
var markers = [];  // 마커를 저장할 배열

function displayMarker(place) {
    var markerPosition;

    if (place.y && place.x) {
        markerPosition = new kakao.maps.LatLng(place.y, place.x);
    } else {
        markerPosition = place;
    }

    // 새로운 마커를 생성
    var marker = new kakao.maps.Marker({
        map: map,
        position: markerPosition
    });

    // 마커를 markers 배열에 저장
    markers.push(marker);

    // 인포윈도우 생성
    var infowindow = new kakao.maps.InfoWindow({
        content: '<div style="padding:5px;">' + (place.place_name || "현 위치") + '</div>'
    });

    // 마커 클릭 시 인포윈도우 열기
    kakao.maps.event.addListener(marker, 'click', function () {
        infowindow.open(map, marker);
    });
}

// 기존 마커를 모두 제거하는 함수
function removeMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);  // 각 마커를 지도에서 제거
    }
    markers = [];  // 마커 배열 초기화
}

document.getElementById('searchInput').addEventListener('keydown', function (event) {
    if (event.key === 'Enter') {  // 엔터 키가 눌리면
        searchFunction();         // 검색 실행
    }
});

function toggleNewBox() {
    var newBox = document.getElementById('newBox');
    if (newBox.style.display === 'none') {
        newBox.style.display = 'block'; // 새로운 박스 보이기
    } else {
        newBox.style.display = 'none'; // 새로운 박스 숨기기
    }
}