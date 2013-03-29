
first: release

clean:
	ant clean

release: sign_release

sign_release: ant_release
	/bin/cp bin/pimote-release.apk bin/pimote-release-`git curtag | sed -e "s/v//"`.apk

ant_release: FORCE
	ant release

debug: ant_debug

ant_debug: FORCE
	ant debug

FORCE:
