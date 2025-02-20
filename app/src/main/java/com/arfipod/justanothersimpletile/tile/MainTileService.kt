package com.arfipod.justanothersimpletile.tile

import android.content.Context
import androidx.wear.protolayout.ColorBuilders.argb
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.material.Colors
import androidx.wear.protolayout.material.Text
import androidx.wear.protolayout.material.Typography
import androidx.wear.protolayout.material.layouts.PrimaryLayout
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.tooling.preview.Preview
import androidx.wear.tiles.tooling.preview.TilePreviewData
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.SuspendingTileService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val RESOURCES_VERSION: String = "0"

/**
 * Skeleton for a tile with no images.
 */
@OptIn(ExperimentalHorologistApi::class)
class MainTileService : SuspendingTileService() {

    override suspend fun resourcesRequest(
        requestParams: RequestBuilders.ResourcesRequest
    ) = resources(requestParams)

    override suspend fun tileRequest(
        requestParams: RequestBuilders.TileRequest
    ) = tile(requestParams, this)
}

private fun resources(
    requestParams: RequestBuilders.ResourcesRequest
): ResourceBuilders.Resources {
    return ResourceBuilders.Resources.Builder()
        .setVersion(RESOURCES_VERSION)
        .build()
}

private fun tile(
    requestParams: RequestBuilders.TileRequest,
    context: Context,
): TileBuilders.Tile {
    val singleTileTimeline: TimelineBuilders.Timeline = TimelineBuilders.Timeline.Builder()
        .addTimelineEntry(
            TimelineBuilders.TimelineEntry.Builder()
                .setLayout(
                    LayoutElementBuilders.Layout.Builder()
                        .setRoot(tileLayout(requestParams, context))
                        .build()
                )
                .build()
        )
        .build()

    // TODO Adjust refresh interval so it is refreshed exactly once time is updated
    return TileBuilders.Tile.Builder()
        .setResourcesVersion(RESOURCES_VERSION)
        .setFreshnessIntervalMillis(60 * 1000)
        .setTileTimeline(singleTileTimeline)
        .build()
}

private fun tileLayout(
    requestParams: RequestBuilders.TileRequest,
    context: Context,
): LayoutElementBuilders.LayoutElement {

    val now: LocalDateTime = LocalDateTime.now()
    val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formattedTime: String = now.format(timeFormatter)
    val formattedDate: String = now.format(dateFormatter)

    return PrimaryLayout.Builder(requestParams.deviceConfiguration)
        .setResponsiveContentInsetEnabled(true)
        .setContent(
            LayoutElementBuilders.Column.Builder()
                .addContent(
                    Text.Builder(context, formattedTime)
                        .setColor(argb(Colors.DEFAULT.onSurface))
                        .setTypography(Typography.TYPOGRAPHY_DISPLAY1)
                        .build()
                )
                .addContent(
                    Text.Builder(context, formattedDate)
                        .setColor(argb(Colors.DEFAULT.onSurface))
                        .setTypography(Typography.TYPOGRAPHY_CAPTION1)
                        .build()
                )
                .build()
        ).build()
}

@Preview(device = WearDevices.SMALL_ROUND)
@Preview(device = WearDevices.LARGE_ROUND)
fun tilePreview(context: Context) = TilePreviewData(::resources) {
    tile(it, context)
}